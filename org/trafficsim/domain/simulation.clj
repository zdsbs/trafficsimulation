(ns org.trafficsim.domain.simulation
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car] [org.trafficsim.domain.world])
)

(defn make-accum []
	(let [ c (atom[])]
		{:update #(swap! c conj %)
		 :reset #(reset! c [])
		 :get #(deref c)}))

(defn observe-world [cars]
	(let [car-pairs (ordered-car-pair (vec cars))]
		(distances-between-cars car-pairs)))

(defn get-car-reactions [cars observable-world-state]
	(all-speed-changes cars observable-world-state))

(defn apply-car-reactions [cars reactions]
	(let [cars-with-new-speeds (apply-new-speeds cars reactions)]
		(move-all-cars cars-with-new-speeds)))

(defn simulation-tick [cars]
	(let [
		observable-world-state (observe-world cars) 
		speed-changes (all-speed-changes cars observable-world-state)]
		(apply-car-reactions cars speed-changes)))

(defn run-sim [num-ticks initial-cars & [side-effect]]
	(loop [ticks-left num-ticks cars initial-cars]
		(if (not (nil? side-effect))
			(side-effect cars))
		(if (= ticks-left 0) 
			cars
			(recur (dec ticks-left) (simulation-tick cars)))))




