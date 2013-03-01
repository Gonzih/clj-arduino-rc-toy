(ns clj-rc-toy.arduino
  (:require [clodiuno.core    :refer :all])
  (:require [clodiuno.firmata :refer :all]))

(defn init-board [port]
  (System/setProperty "gnu.io.rxtx.SerialPorts" port)
  (let [board (arduino :firmata port :baudrate 9600)]
    (Thread/sleep 5000)
    board))

(defn speed [board s]
  (digital-write board 11 s)
  (digital-write board 10 s))

(defn go [board]
  (digital-write board 3 LOW)
  (digital-write board 5 LOW)
  (digital-write board 2 HIGH)
  (digital-write board 4 HIGH)
  (speed board HIGH))

(defn back [board]
  (digital-write board 3 HIGH)
  (digital-write board 5 HIGH)
  (digital-write board 2 LOW)
  (digital-write board 4 LOW)
  (speed board HIGH))

(defn stop [board]
  (speed board LOW))

(def left-pins  [2 3 11])
(def right-pins [4 5 10])

(def port (System/getenv "PORT"))
(def board (init-board port))

(defn init-pin [pin]
  (pin-mode board pin OUTPUT))

(map (partial map init-pin) [left-pins right-pins])


(defn move-gear [val [i1 i2 e]]
  (cond (zero? val) (do
                      (digital-write board i1 LOW)
                      (digital-write board i2 LOW)
                      (digital-write board e LOW)
                      )
        (> 0 val)   (do
                      (digital-write board i1 LOW)
                      (digital-write board i2 HIGH)
                      (digital-write board e HIGH))
        (< 0 val)   (do
                      (digital-write board i1 HIGH)
                      (digital-write board i2 LOW)
                      (digital-write board e HIGH))))

(defn move [[_ _ type] {val :val}]
  (cond (#{"y"}  type) (move-gear val left-pins)
        (#{"ry"} type) (move-gear val right-pins)))

(defn event [type value]
  (move type value))


(defn on-event [type value key]
  (println (str "on event " type " " value " " key)))
