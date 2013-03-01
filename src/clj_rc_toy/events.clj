(ns clj-rc-toy.events
  (:require [clj-rc-toy.arduino :as arduino])
  (import (net.java.games.input Component Controller)))

(defn- find-name [item]
  (cond
    (instance? Component) (.getName item)
    (instance? Controller) (.getName item)
    :else (.toString item)))

(defn mk-event-type [controller component]
  [:jinput controller (find-name component)])

(defn event-send [event-type value]
  (arduino/event event-type {:val value}))

(defn send-diffs
  "Send diffs if there are any"
  [controller diffs]
  (when (not= diffs {})
    (doseq [[k v] diffs]
      (event-send (mk-event-type controller k) v))))

(defn controller-event-handlers
  "parameters are a controller and a map of components to functions"
  [controller handlers]
  (when controller
    (doseq [[k v] handlers]
      (arduino/on-event (mk-event-type controller k) v (keyword k)))))
