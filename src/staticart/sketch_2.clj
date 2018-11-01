(ns staticart.sketch-2
  (:require [quil.core :as q :include-macros true]
            [staticart.draw :as d]
            [staticart.noise :as noise]
            [staticart.settings :refer [settings]]))

(defn draw []
  (d/background 360)
  (d/stroke 0)
  #_(noise/noise-image 6 0.01)
  (noise/noise-image-turb 6 0.01 #(* 420 (* (Math/sin (* % 0.01)) (Math/sin (* %2 0.01)))))
  (d/save "output.jpg"))

