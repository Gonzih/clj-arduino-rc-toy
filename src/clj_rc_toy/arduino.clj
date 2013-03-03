(ns clj-rc-toy.arduino
  (:require [clodiuno.core    :refer :all])
  (:require [clodiuno.firmata :refer :all]))

(defn init-board [port]
  (System/setProperty "gnu.io.rxtx.SerialPorts" port)
  (let [board (arduino :firmata port :baudrate 9600)]
    (Thread/sleep 5000)
    board))

(def left-pins  [2 3 11])
(def right-pins [4 5 10])

(defn port [] (System/getenv "PORT"))
(def board (delay (init-board (port))))

(defn init-pin [pin]
  (pin-mode board pin OUTPUT))

(map (partial map init-pin) [left-pins right-pins])

(defn move-gear [val [i1 i2 e]]
  (cond (zero? val) (do
                      (digital-write @board i1 LOW)
                      (digital-write @board i2 LOW)
                      (digital-write @board e LOW)
                      )
        (> 0 val)   (do
                      (digital-write @board i1 LOW)
                      (digital-write @board i2 HIGH)
                      (digital-write @board e HIGH))
        (< 0 val)   (do
                      (digital-write @board i1 HIGH)
                      (digital-write @board i2 LOW)
                      (digital-write @board e HIGH))))

(defn move [[_ _ type] {val :val}]
  (cond (#{"y"}  type) (move-gear val left-pins)
        (#{"ry"} type) (move-gear val right-pins)))

(defn event [type value]
  (move type value))

(defn on-event [type value key]
  (println (str "on event " type " " value " " key)))
