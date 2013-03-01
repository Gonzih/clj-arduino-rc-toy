(ns clj-rc-toy.core
  (:require [clj-rc-toy.input    :refer :all]
            [clj-rc-toy.finders  :refer :all]
            [clj-rc-toy.events   :refer :all]
            [clj-rc-toy.handlers :refer :all]
            [clj-rc-toy.loop     :refer :all]))

(defn -main [& args]
  (let [xbox (find-controller "X-Box")]
    ;(controller-event-handlers
      ;xbox
      ;{BUTTON0 #(println (:val %))
       ;BUTTON1 #(when (button-pressed? (:val %)) (tune-frere mout 1 :tubular-bells ))
       ;BUTTON2 #(when (button-pressed? (:val %)) (tune-frere mout 2 :trumpet ))
       ;BUTTON3 #(when (button-pressed? (:val %)) (tune-frere mout 3 :electric-guitar-clean ))
       ;BUTTON4 #(when (button-pressed? (:val %)) (percuss mout :cowbell ))
       ;BUTTON5 #(when (button-pressed? (:val %)) (percuss mout :open-triangle ))
       ;BUTTON6 #(when (button-pressed? (:val %)) (percuss mout :maracas ))
       ;BUTTON7 #(when (button-pressed? (:val %)) (percuss mout :tambourine ))
       ;X-AXIS #(println "X = " (:val %))
       ;HAT #(condp hat-direction? (:val %)
              ;HAT_N (println "hat n")
              ;HAT_W (println "hat w")
              ;HAT_E (println "hat e")
              ;HAT_S (println "hat s")
              ;nil)})
    (let [daemon (start-input)]
      (while (not= (read-line) "q"))
      (stop-input daemon))))
