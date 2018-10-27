(ns staticart.core
  (:require [quil.core :as q]
            [staticart.settings :refer [settings]]
            [staticart.sketch-1 :as sketch]))

(defn setup []
  (q/no-loop)
  (q/color-mode :hsb))

(q/defsketch staticart
  :title "My Quil Sketch"
  :size [(:width settings) (:height settings)]
  :setup setup
  :draw sketch/draw
  :features [:keep-on-top])


