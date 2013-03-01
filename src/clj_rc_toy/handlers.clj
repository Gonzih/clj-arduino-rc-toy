(ns clj-rc-toy.handlers)

(defn button [n] (str "Button " n))

(def BUTTON0 (button 0))
(def BUTTON1 (button 1))
(def BUTTON2 (button 2))
(def BUTTON3 (button 3))
(def BUTTON4 (button 4))
(def BUTTON5 (button 5))
(def BUTTON6 (button 6))
(def BUTTON7 (button 7))
(def BUTTON8 (button 8))
(def BUTTON9 (button 9))
(def BUTTON10 (button 10))
(def BUTTON11 (button 11))
(def BUTTON12 (button 12))
(def BUTTON13 (button 13))
(def BUTTON14 (button 14))
(def BUTTON15 (button 15))

(def HAT "Hat Switch")

(def X-AXIS "X Axis")
(def Y-AXIS "Y Axis")
(def Z-AXIS "Z Axis")

(def HAT_DIRECTIONS {:north-west 0.125
                     :north 0.25
                     :north-east 0.375
                     :east 0.5
                     :south-east 0.625
                     :south 0.75
                     :south-west 0.875
                     :west 1.0})

(def HAT_NW (:north-west HAT_DIRECTIONS))
(def HAT_N  (:north      HAT_DIRECTIONS))
(def HAT_NE (:north-east HAT_DIRECTIONS))
(def HAT_E  (:east       HAT_DIRECTIONS))
(def HAT_SE (:south-east HAT_DIRECTIONS))
(def HAT_S  (:south      HAT_DIRECTIONS))
(def HAT_SW (:south-west HAT_DIRECTIONS))
(def HAT_W  (:west       HAT_DIRECTIONS))

(defn hat-direction? [direction value]
  (= direction value))

(defn hat-released? [value]
  (= 0.0 value))

(defn button-pressed? [value]
  (= 1.0 value))

(defn button-released? [value]
  (= 0.0 value))
