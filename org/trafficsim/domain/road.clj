(ns org.trafficsim.domain.road)

;next week (this week) we will write a drivable space generator
;(drivables_space 100 10)

;(def stuff [
;[1 1 1]
;[1 0 1]
;])

(defn cartesian-product
  "All the ways to take one item from each sequence"
  [& seqs]
  (let [v-original-seqs (vec seqs)
	step
	(fn step [v-seqs]
	(let [increment
	(fn [v-seqs]
	(loop [i (dec (count v-seqs)), v-seqs v-seqs]
	(if (= i -1) nil
	(if-let [rst (next (v-seqs i))]
	(assoc v-seqs i rst)
	(recur (dec i) (assoc v-seqs i (v-original-seqs i)))))))]
	(when v-seqs
	(cons (map first v-seqs)
	(lazy-seq (step (increment v-seqs)))))))]
	    (when (every? first seqs)
	      (lazy-seq (step v-original-seqs)))))

(defn all-space [num-roads road-width]
	(let [space-size (fn space-size [num-roads road-width] (- (* 2 road-width num-roads) road-width))]
			(let [size (space-size num-roads road-width)]
				(cartesian-product (range 0 size) (range 0 size)))))

(defn is-road-offset [road-width offset]
		(mod (int (/ (+ road-width offset) road-width)) 2)) 

(defn is-road-coordinate [road-width coord-seq]
	(or 
		(is-road-offset road-width (first coord-seq)) 
		(is-road-offset road-width (second coord-seq))))



;1,000 pixels of road
(def LENGTH 1000)

;right now roads only have length b/c we're limiting ourselves
;to one-lane roads.  In the future it might have other quantities

