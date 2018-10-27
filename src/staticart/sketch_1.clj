(ns staticart.sketch-1
  (:require [quil.core :as q]
            [staticart.screen-locations :refer :all]))

(defn f [t]
  [(* t (q/sin t))
   (* t (q/cos t))])

(defn draw-plot [f from to step]
  (doseq [two-points (->> (range from to step)
                          (map f)
                          (partition 2 1))]
    (apply q/line two-points)))

(defn draw []
  (q/background 200)
  (q/with-translation (mid-point)
   (draw-plot f (w 0.0) (w 1.25) 2)))

