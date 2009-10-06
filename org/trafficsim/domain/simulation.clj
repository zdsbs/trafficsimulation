(ns org.trafficsim.domain.simulation
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car] [org.trafficsim.domain.world])
)

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




(def 
	#^{:doc "a mutable sequence of the world state; each item in the sequence represents the state of the world at that time 'tick'"}
	tick-stack (ref ()))

;create the initial stat of the world by creating the first item in the tick-stack with one car
(dosync (alter tick-stack conj [(struct car 15 1 0)]))

(defn 
	#^{:doc "takes a 'tick-frame', representing the state of the world at one time, and adds it to the stack of all the ticks"}
	add-frame [tick-frame]
	(dosync (alter tick-stack conj tick-frame)))

;loops N times, moving the car for each tick and adding the new tick state to the stack of all tick states
(dotimes [tick 10] 
	(add-frame (move-all-cars (first @tick-stack))))


(defn 
	#^{:doc "randomly adds new cars to the road from time to time"}
	add-cars [car-head-positions]
	; if some criteria
	; (assoc car-head-positions (struct car 15 1) 0)
)

;(println @tick-stack)