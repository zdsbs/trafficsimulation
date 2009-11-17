(ns org.trafficsim.domain.world
	(:use [org.trafficsim.domain.road] [org.trafficsim.domain.car])
)

;world knows about road and cars and their relationship

(def road {:length 1000 :width 14})

(defn 
	#^{:doc "takes an ordered vector of cars and creates a vector of pairs of each car and the car in front of it"}
	ordered-car-pair [cars]
		(partition 2 1 cars))

(defn 
	#^{:doc "calculates the distance between a pair of cars"}
	distance-between-two-cars [[car1 car2]]
	(if (nil? car2)
		IN-FRONT
		(- (:head-position car1) (:length car1) (:head-position car2))))

(defn 
	#^{:doc "takes a set of pairs of cars, sorted, leader to the left"}
	distances-between-cars [car-pairs]
		(cons IN-FRONT (map distance-between-two-cars car-pairs)))

(defn
	#^{:doc "checks alls cars against their following distances and changes their speeds accordingly; does not modify the car, just returns the new speed"}
	all-speed-changes [cars observable-entities]
	(map get-new-target-speed cars observable-entities))

(defn 
	#^{:doc "applies a vector of new speeds to a vector of cars.  The vectors need to be the same size and in the same order."}
	apply-new-speeds [cars new-speeds]
	(map apply-new-speed cars new-speeds))
	
;TODO - Where does this really belong?
(defn 
	#^{:doc "determines whether a car should remain in the vector of active cars; if the car is off the end of the road remove it"}
	keep-car? [car]
	(<= (get-car-tail-position car) (road :length)))

(defn 
	move-car [car]
	(assoc car :head-position (+ (car :head-position) (car :speed))))

(defn 
	#^{:doc "move all cars according to their speeds"}
	move-all-cars [input-cars]
	(filter keep-car? (map move-car input-cars)))

(defn observe-world [cars]
	(let [car-pairs (ordered-car-pair (vec cars))]
		(distances-between-cars car-pairs)))

(defn remove-from [to-remove col] (remove #(= to-remove %) col))
(defn get-other-cars [cars] (map #(remove-from % cars) cars))
(defn get-other-cars-around [cars] (map get-cars-around cars (get-other-cars cars)))

;TODO - Expand this to take in other observables, roads, etc.
(defn observe-world-2 [cars]
	(map #(struct observable-entities %) (get-other-cars-around cars)))
