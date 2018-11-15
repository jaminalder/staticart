(ns staticart.core
  (:require [quil.core :as q]
            [quil.middleware :as mid]
            [staticart.settings :refer [settings]]
            [staticart.sketch-6 :as sketch]))

(defn setup []
  (q/no-loop)
  (q/smooth)
  (q/color-mode :hsb 360 100 100 1.0))

(q/defsketch staticart
  :title "My Quil Sketch"
  :features [:no-bind-output]
  :size [(:width settings) (:height settings)]
  :setup setup
  :draw sketch/draw
  :features [:keep-on-top]
  :middleware [mid/pause-on-error]
  )


