(ns clj-rc-toy.core
  (:require [clj-rc-toy.input    :refer :all]
            [clj-rc-toy.finders  :refer :all]
            [clj-rc-toy.events   :refer :all]
            [clj-rc-toy.handlers :refer :all]
            [clj-rc-toy.loop     :refer :all])
  (:gen-class))

(defn -main [& args]
  (let [xbox (find-controller "X-Box")]
    (let [daemon (start-input)]
      (while (not= (read-line) "q"))
      (stop-input daemon))))
