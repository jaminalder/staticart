(ns staticart.sketch-5
  (:require [clojure.tools.trace :refer :all]
            [quil.core :as q :include-macros true]
            [staticart.color :as c]
            [staticart.draw :as d]
            [staticart.noise :as noise]
            [staticart.settings :refer [settings]]
            [staticart.subdivision :as sub]
            [thi.ng.geom.core :as g]
            [thi.ng.geom.matrix :refer [M32]]
            [thi.ng.geom.triangle :as tri]
            [thi.ng.geom.utils :as gu]
            [thi.ng.geom.utils.delaunay :as del]
            [thi.ng.geom.vector :as v :refer [vec2]]
            [thi.ng.math.core :as m]
            [thi.ng.math.macros :as mm]))

#_(set! *warn-on-reflection* false) ;; To avoid accidental reflection

(defn make-int-matrix [width height]
  ^"[[I" (make-array Integer/TYPE height width))

(def ^"[[I" matrix (make-int-matrix (:width settings) (:height settings)))

(defmacro aget2i [a x y]
  `(aget ^"[I" (aget ~a ~y) ~x))

(defmacro aset2i [a x y v]
  `(aset ^"[I" (aget ~a ~y) ~x ~v))

(defn on-points [points f]
  (time (doseq [[x y] points] (f matrix x y))))

(defn on-matrix [f]
  (let [ps (for [x (range (alength ^"[I" (aget matrix 0)))
                 y (range (alength matrix))] [x y])]
    (on-points ps f)))

(defn draw-matrix []
  (on-matrix (fn [^"[[I" m x y] (q/set-pixel x y (aget2i m x y)))))

(defn points-in-triangle [triangle]
  (let [tri (tri/triangle2 triangle)
        b (g/bounds tri)
        x-start (:x (:p b))
        y-start (:y (:p b))
        x-end (+ x-start (:x (:size b)))
        y-end (+ y-start (:y (:size b)))
        xs (range x-start x-end)
        ys (range y-start y-end)
        ps (for [x xs y ys] [x y])]
    (filter #(g/contains-point? tri %) ps)))

(defn noise-function [color octaves scale turbulence]
  (let [r 3]
   (fn [^"[[I" m x y]
     (let [x' (+ x r (turbulence x y))
           y' (+ y r (turbulence y x))
           n (noise/octave-noise2 x' y' scale octaves)
           newb (+ (nth color 2) (* n 20))
           newh (- (nth color 0) (* n 20))]
       (aset2i m x y ^int (q/color (nth color 0) (nth color 1) newb 1))))))


(defn draw []
  (d/background 0 0 0 0)

  #_(on-matrix (noise-function [7 92 58] 6 0.001))

  #_(let [ps (points-in-triangle [(vec2 200 50) (vec2 400 400) (vec2 50 400)])]
    (on-points ps (noise-function (c/pick-color c/palette-dark-red 1) 6 0.01)))

  (let [l (/ 500 10)
        starts (for [x (range 0 500 l) y (range 0 500 l)] [x y])
        blocks (map #(for [x (range (nth % 0) (+ (nth % 0) l)) y (range (nth % 1) (+ (nth % 1) l))] [x y]) starts)]
    (doseq [ps blocks]
      (on-points ps (noise-function
                     (c/pick-color c/palette-dark-red 1) 6 0.006
                     #(* 420 (* (Math/sin (* % (/ Math/PI 25))) (Math/sin (* %2 (/ Math/PI 25))))))))
    (draw-matrix)
    #_(doseq [t tris] (d/vertex t true)))

  (d/save "output.jpg"))


;(noise/noise-image-turb 6 0.01 #(* 420 (* (Math/sin (* % 0.01)) (Math/sin (* %2 0.01)))))
