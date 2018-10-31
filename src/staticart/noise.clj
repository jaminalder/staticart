(ns staticart.noise
  (:require [quil.core :as q :include-macros true]
            [staticart.settings :refer [settings]]
            [thi.ng.color.core :as col]
            [thi.ng.color.gradients :as grad]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.svg.core :as svg]
            [thi.ng.geom.vector :as v]
            [thi.ng.math.core :as m]
            [thi.ng.math.noise :as noise]))

(def octave-pow2 [1.0 2.0 4.0 8.0 16.0 32.0 64.0])

(defn noise-in-octave
  [x y s o]
  (let [o (nth octave-pow2 o)
        s (* s o)]
    (/ (noise/noise2 (* x s) (* y s)) o)))

(defn octave-noise2
  [x y s o]
  (loop [n 0.0, o o]
    (if (>= o 0)
      (recur (+ n (noise-in-octave x y s o)) (dec o))
      n)))

(defn noise-image-gray
  [width octaves scale]
  (let [r (rand-int 500)]
    (doseq [y (range (:height settings))
            x (range (:width settings))
            :let [n (octave-noise2 (+ r x) (+ r y) scale octaves)
                  n (+ (* n 20) 60)]]
      (q/set-pixel x y (q/color 50 100 n 1)))))

(comment

  (time (noise-image-gray 300 6 0.01))
  )



