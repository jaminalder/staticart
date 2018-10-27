(ns staticart.sketch-1
  (:require [staticart.draw :as d]
            [staticart.screen-locations :refer :all]
            [staticart.subdivision :as sub]))


(defn draw []
  (d/background 360)
  (let [tri [(point-on-screen 0.5 0.1)
             (point-on-screen 0.1 0.9)
             (point-on-screen 0.9 0.9)]
        divided (sub/rec-divide-tris [tri] 5)]
    (doseq [tri divided] (d/vertex tri true)))
  )

