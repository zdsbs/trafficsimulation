(ns org.trafficsim.domain.road
	(:use clojure.contrib.combinatorics clojure.walk)
)

;next week (this week) we will write a drivable space generator
;(drivables_space 100 10)

(defn space-size [num-roads road-width]
	(- (* 2 road-width num-roads) road-width))

(defn all-space [num-roads road-width]
	(let [size (space-size num-roads road-width)]
		(cartesian-product (range 0 size) (range 0 size))))

(defn is-road-offset [road-width offset]
		(mod (int (/ (+ road-width offset) road-width)) 2)) 

(defn is-road-coordinate [road-width coord-seq]
	(bit-or 
		(is-road-offset road-width (first coord-seq)) 
		(is-road-offset road-width (second coord-seq))))

;1,000 pixels of road
(def LENGTH 1000)

;right now roads only have length b/c we're limiting ourselves
;to one-lane roads.  In the future it might have other quantities

(defn no-op-formatter [coord road-width]
	coord)
	
(defn actual-formatter [coord road-width]
	(is-road-coordinate road-width (seq coord)))

(defn space-row [num-roads road-width row-num formatter]
	(loop [col-num 0 max-col (space-size num-roads road-width) row []]
		(if (= col-num (dec max-col))
			(conj row (formatter [row-num col-num] road-width))
			(recur (inc col-num) max-col (conj row (formatter [row-num col-num] road-width))))))

(defn space [num-roads road-width formatter]
	(loop [row-num 0 max-row (space-size num-roads road-width) space []]
		(if (= row-num (dec max-row))
			(conj space (space-row num-roads road-width row-num formatter))
			(recur (inc row-num) max-row (conj space (space-row num-roads road-width row-num formatter))))))	

(defn print-space [space] 
	(map prn space))