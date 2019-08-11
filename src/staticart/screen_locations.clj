(ns staticart.screen-locations
  (:require [staticart.settings :refer [settings]]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]))

(defn h
  ([] (h 1.0))
  ([value] (* (:height settings) value)))

(defn w
  ([] (w 1.0))
  ([value] (* (:width settings) value)))

(defn mid-point [] (vec2 (w 0.5) (h 0.5)))

(defn point-on-screen [ww hh] (vec2 (w ww) (h hh)))

(def pos point-on-screen)

(def screen-point (vec2 (:width settings) (:height settings)))

(def screen-mid-point (vec2 (/ (:width settings) 2) (/ (:height settings) 2)))

(defn scale-to-screen [p]
  (m/* p screen-point))

(defn scale-to-mid-screen [p]
  (m/* p screen-mid-point))

(def all-points (for [x (range (:width settings))
                      y (range (:height settings))]
                  (vec2 x y)))
