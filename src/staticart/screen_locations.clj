(ns staticart.screen-locations
  (:require [staticart.settings :refer [settings]]
            [thi.ng.geom.vector :as v :refer [vec2]]))

(defn h
  ([] (h 1.0))
  ([value] (* (:height settings) value)))

(defn w
  ([] (w 1.0))
  ([value] (* (:width settings) value)))

(defn mid-point [] (vec2 (w 0.5) (h 0.5)))

(defn point-on-screen [ww hh] (vec2 (w ww) (h hh)))

(def pos point-on-screen)
