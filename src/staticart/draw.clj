(ns staticart.draw
  (:require [quil.core :as q :include-macros true]))

(def background q/background)

(defn point [p]
  (q/point (nth p 0) (nth p 1)))

(defn line [p1 p2]
  (q/line (nth p1 0) (nth p1 1) (nth p2 0) (nth p2 1)))

(defn vertex [ps & closed]
  (q/begin-shape)
  (doseq [p ps] (apply q/vertex p))
  (when closed (apply q/vertex (first ps)))
  (q/end-shape))

(defn curve-through-points [ps]
  (q/begin-shape)
  (doseq [p ps] (apply q/curve-vertex p))
  (q/end-shape))

(defn debug-point [p]
  (q/ellipse (nth p 0) (nth p 1) 5 5))


