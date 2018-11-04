(ns staticart.noise
  (:require [quil.core :as q :include-macros true]
            [staticart.settings :refer [settings]]
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

#_(defn noise-image
  [octaves scale]
  (let [r (rand-int 500)]
    (doseq [y (range (:height settings))
            x (range (:width settings))
            :let [n (octave-noise2 (+ r x) (+ r y) scale octaves)
                  n (+ (* n 20) 60)]]
      (q/set-pixel x y (q/color 50 100 n 1)))))

(defn noise-image
  [ps color octaves scale]
  (let [r (rand-int 500)]
    (doseq [p ps
            :let [m (octave-noise2 (+ r (first p)) (+ r (last p)) scale octaves)
                  n (+ (nth color 2) (* m 20))]]
      (q/set-pixel  (first p) (last p) (q/color (nth color 0) (nth color 1) n 1)))))

(defn noise-image-turb
  [octaves scale turbulence]
  (let [r (rand-int 500)]
    (doseq [y (range (:height settings))
            x (range (:width settings))
            :let [x' (+ x r (turbulence x y))
                  y' (+ y r (turbulence y x))
                  n (octave-noise2 x' y' scale octaves)
                  n (+ (* n 30) 40)
                  m (octave-noise2 (+ x r) (+ y r) 0.02 octaves)
                  m (+ (* m 15) 70)]]
      (q/set-pixel x y (q/color n 90 m 1)))))


(comment

  (time (noise-image-gray 300 6 0.01))
  )



