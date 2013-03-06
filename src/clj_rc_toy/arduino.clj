(ns clj-rc-toy.arduino
  (:require [clodiuno.core    :refer :all]
            [clodiuno.firmata :refer :all]
            [clojure.math.numeric-tower :as math]))

(defn init-pin [board pin]
  (pin-mode board pin OUTPUT))

(defn analog-pin [board pin]
  (pin-mode board pin PWM))

(def left-pins  [2 3 11])
(def right-pins [4 5 10])

(defn init-board [port]
  (System/setProperty "gnu.io.rxtx.SerialPorts" port)
  (let [board (arduino :firmata port :baudrate 9600)
        pins [left-pins right-pins]
        e-pins (flatten (map (partial take-last 1) pins))
        c-pins (flatten (map (partial take      2) pins))]
    (doall (map (partial analog-pin board) e-pins))
    (doall (map (partial init-pin   board) c-pins))
    board))

(defn port [] (System/getenv "PORT"))

(def board (delay (init-board (port))))

(defn move-gear [val [i1 i2 e]]
  (let [pwm-val (-> val math/abs (* 250) int (+ 5))]
    (cond (zero? val) (do
                        (digital-write @board i1 LOW)
                        (digital-write @board i2 LOW)
                        (analog-write  @board e  LOW))
          (> 0 val)   (do
                        (digital-write @board i1 LOW)
                        (digital-write @board i2 HIGH)
                        (analog-write  @board e  pwm-val))
          (< 0 val)   (do
                        (digital-write @board i1 HIGH)
                        (digital-write @board i2 LOW)
                        (analog-write  @board e  pwm-val)))))

(defn move [[_ _ type] {val :val}]
  (cond (#{"y"}  type) (move-gear val left-pins)
        (#{"ry"} type) (move-gear val right-pins)))

(defn event [type value]
  (move type value))

(defn on-event [type value key]
  (println (str "on event " type " " value " " key)))
