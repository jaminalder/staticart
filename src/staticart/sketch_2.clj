(ns staticart.sketch-2
  (:require [quil.core :as q :include-macros true]
            [staticart.draw :as d]
            [staticart.noise :as noise]
            [staticart.settings :refer [settings]]))

(defn draw []
  (d/background 360)
  (d/stroke 0)
  (noise/noise-image-gray 300 6 0.01)
  #_(doseq [y (range (:height settings))
          x (range (:width settings))]
    (q/set-pixel x y (q/color (* 0.7 x) (* 0.2 y) 80 1)))
  (d/save "output.jpg"))

