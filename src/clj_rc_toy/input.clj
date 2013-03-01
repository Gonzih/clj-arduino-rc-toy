(ns clj-rc-toy.input
  (:require [clojure.math.numeric-tower :as math]))

(def DELAY 30)

(def ZERO 0.0)

(def NEAR_ZERO 0.1)

(def CONSIDER_SAME 0.1)

(def LIMIT 1.0)

(defn poll-state [controller]
  (if (.poll controller)
    (into {} (map #(-> [% (.getPollData %)]) (.getComponents controller)))
    {}))

(defn poll-states [controllers]
  (into {} (map #(-> [% (poll-state %)]) controllers)))

(defn send-and-store [controller old-state send-fn diffs]
  (send-fn controller diffs)
  [controller (conj old-state diffs)])

(defn different-values? [old new]
  (and
    (not= old new)
    (or
      (> (math/abs (- old new)) CONSIDER_SAME)
      (= LIMIT (math/abs new))
      (= ZERO (math/abs new)))))

(defn round-to-zero [[k v]]
  (if (< (math/abs v) NEAR_ZERO)
    [k ZERO]
    [k v]))

(defn diff-state
  "Find the differences between two states of a controller"
  [previous-state current-state]
  (into {}
    (map round-to-zero
      (filter
        (fn [[k v]] (different-values? (get previous-state k) v)) current-state))))

(defn new-state [controller old-state send-fn]
  (send-and-store controller old-state send-fn (diff-state old-state (poll-state controller))))
