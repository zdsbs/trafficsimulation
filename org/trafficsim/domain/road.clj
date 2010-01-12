(ns org.trafficsim.domain.road
	(:use clojure.contrib.combinatorics clojure.walk)
)

(defn space-size [num-roads road-width]
	(- (* 2 road-width num-roads) road-width))

(defn is-coordinate-on-road? [road-width coordinate]
		(mod (int (/ (+ road-width coordinate) road-width)) 2)) 

(defn is-point-on-road? [road-width point]
	(bit-or 
		(is-coordinate-on-road? road-width (first point)) 
		(is-coordinate-on-road? road-width (second point))))

(defn no-op-formatter [point road-width]
	point)
	
(defn binary-formatter [point road-width]
	(is-point-on-road? road-width (seq point)))

(defn space-row [num-roads road-width row-num point-formatter]
	(loop [col-num 0 max-col (space-size num-roads road-width) row []]
		(if (= col-num (dec max-col))
			(conj row (point-formatter [row-num col-num] road-width))
			(recur (inc col-num) max-col (conj row (point-formatter [row-num col-num] road-width))))))

(defn space [num-roads road-width point-formatter]
	(loop [row-num 0 max-row (space-size num-roads road-width) space []]
		(if (= row-num (dec max-row))
			(conj space (space-row num-roads road-width row-num point-formatter))
			(recur (inc row-num) max-row (conj space (space-row num-roads road-width row-num point-formatter))))))	

(defn print-space [space] 
	(map prn space))