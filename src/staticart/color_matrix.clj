(ns staticart.color-matrix
  (:require [quil.core :as q :include-macros true]
            [staticart.settings :refer [settings]]
            [thi.ng.color.core :as c]))

(set! *warn-on-reflection* false) ;; To avoid accidental reflection

(defn make-color-matrix [width height]
  ^"[[Lthi.ng.color.core.RGBA;" (make-array (Class/forName "thi.ng.color.core.RGBA") height width))

(def ^"[[Lthi.ng.color.core.RGBA;" cmatrix (make-color-matrix (:width settings) (:height settings)))

(defmacro aget2c [a x y]
  `(aget ^"[Lthi.ng.color.core.RGBA;" (aget ~a ~y) ~x))

(defmacro aset2c [a x y v]
  `(aset ^"[Lthi.ng.color.core.RGBA;" (aget ~a ~y) ~x ~v))

(defn on-points [points f]
  (time (doseq [[x y] points]
          (when (and
                 (< x (alength (aget cmatrix 0)))
                 (> x 0)
                 (< y (alength cmatrix))
                 (> y 0))
            (f cmatrix x y)))))

(defn on-matrix [f]
  (let [ps (for [x (range (alength ^"[Lthi.ng.color.core.RGBA;" (aget cmatrix 0)))
                 y (range (alength cmatrix))] [x y])]
    (on-points ps f)))

(defn set-function [coloring-function]
  (fn [^"[[Lthi.ng.color.core.RGBA;" mt x y]
    (aset2c mt x y (coloring-function x y))))

(defn coloring-on-matrix [coloring-function]
  (on-matrix (set-function coloring-function)))

(defn reset-matrix [color]
  (coloring-on-matrix (fn [x y] color)))

(defn draw-matrix []
  (on-matrix (fn [^"[[Lthi.ng.color.core.RGBA;" m x y]
               (q/set-pixel x y (apply q/color (deref (aget2c m x y)))))))


