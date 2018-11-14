(ns staticart.matrix
  (:require [quil.core :as q :include-macros true]
            [staticart.settings :refer [settings]]))

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


