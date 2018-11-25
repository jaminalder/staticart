(ns staticart.sketch-8
  (:require [clojure.tools.trace :refer :all]
            [quil.core :as q :include-macros true]
            [staticart.color-matrix :as cm]
            [staticart.draw :as d]
            [staticart.noise :as noise]
            [staticart.settings :refer [settings]]
            [staticart.subdivision :as sub]
            [thi.ng.color.core :as c]
            [thi.ng.color.gradients :as grad]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.matrix :refer [M32]]
            [thi.ng.geom.triangle :as tri]
            [thi.ng.geom.utils :as gu]
            [thi.ng.geom.utils.delaunay :as del]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]
            [thi.ng.math.macros :as mm]))

(def palette_1 [(c/as-rgba (c/css "rgba(51,92,103,1)"))
                (c/as-rgba (c/css "rgba(255,243,176,1)"))
                (c/as-rgba (c/css "rgba(224,159,62,1)"))
                (c/as-rgba (c/css "rgba(158,42,43,1)"))
                (c/as-rgba (c/css "rgba(84,11,14,1)"))])

(def palette_2 [(c/as-rgba (c/css "rgba(91,192,235,1)"))
                (c/as-rgba (c/css "rgba(253,231,76,1)"))
                (c/as-rgba (c/css "rgba(155,197,61,1)"))
                (c/as-rgba (c/css "rgba(229,89,52,1)"))
                (c/as-rgba (c/css "rgba(250,121,33,1)"))])

(def palette_3 [(c/as-rgba (c/css "rgba(60,21,24,1)"))
                (c/as-rgba (c/css "rgba(105,20,14,1)"))
                (c/as-rgba (c/css "rgba(164,66,0,1)"))
                (c/as-rgba (c/css "rgba(213,137,54,1)"))
                (c/as-rgba (c/css "rgba(242,243,174,1)"))])

(def palette_4 [(c/as-rgba (c/css "rgba(27,153,139,1)"))
                (c/as-rgba (c/css "rgba(45,48,71,1)"))
                (c/as-rgba (c/css "rgba(255,253,130,1)"))
                (c/as-rgba (c/css "rgba(255,155,113,1)"))
                (c/as-rgba (c/css "rgba(232,72,85,1)"))])

(def palette_5 [(c/as-rgba (c/css "rgba(51,92,103,1)"))
                (c/as-rgba (c/css "rgba(255,243,176,1)"))
                (c/as-rgba (c/css "rgba(224,159,62,1)"))
                (c/as-rgba (c/css "rgba(158,42,43,1)"))
                (c/as-rgba (c/css "rgba(84,11,14,1)"))])

(def palette_6 [(c/as-rgba (c/css "rgba(237,106,90,1)"))
                (c/as-rgba (c/css "rgba(244,241,187,1)"))
                (c/as-rgba (c/css "rgba(155,193,188,1)"))
                (c/as-rgba (c/css "rgba(92,164,169,1)"))
                (c/as-rgba (c/css "rgba(230,235,224,1)"))])

(defn random-grad [palette steps]
  (grad/cosine-gradient
   steps
   (grad/cosine-coefficients (rand-nth palette) (rand-nth palette))))

(defn shuffled-random-grad [palette steps] (shuffle (random-grad palette steps)))

(defn pth [coll fact low high]
  (nth coll (int (m/map-interval-clamped fact low high 0 (- (count coll) 1)))))

(defn get-coloring-function []
  (let [grad1 (shuffled-random-grad palette_1 40)
        grad2 (shuffled-random-grad palette_1 40)
        grad3 (shuffled-random-grad palette_1 40)]
    (fn [n]
      (cond
        (< n -0.15) (pth grad1 n -1 -0.15)
        (< n 0.15) (pth grad2 n -0.15 0.15)
        (>= n 0.15) (pth grad3 n 0.15 1)))))

(defn noise-function [coloring-function octaves scale turb]
  (let [r (rand 1000)]
   (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
     (let [[x' y'] (map #(+ r %) (turb x y))
           n (noise/octave-noise2 x' y' scale octaves)
           newcolor (coloring-function n)]
       (cm/aset2c mt x y newcolor)))))

(defn draw []
  
  (dotimes [n 50]
    (d/background 0 0 1 1)

    (let [oct (+ 1 (rand-int 4))
         scale (+ 0.0005 (rand 0.001))
         offset (+ 2 (rand-int 2))]

     (cm/on-matrix (noise-function (get-coloring-function) oct scale
                                   (fn [x y] [(* x 1) (* y (rand-int offset))]))))

   (cm/draw-matrix)

   (d/save (str "tmpout/output" (System/currentTimeMillis) ".tiff"))))

