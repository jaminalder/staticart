(ns staticart.random-helper
  (:require [thi.ng.geom.vector :as v :refer [vec2]])
  (:import [java.util Random]))

(def jrand (Random.))

(defn gauss [mean variance]
  (+ mean (* variance (.nextGaussian jrand))))

(defn gauss-variate [p variance]
  (vec2 (gauss (:x p) variance) (gauss (:y p) variance)))

(defn gauss-variate-points [ps variance]
  (map #(gauss-variate % variance) ps))
