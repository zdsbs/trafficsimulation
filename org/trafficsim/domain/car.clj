(ns org.trafficsim.domain.car)

(def 
	#^{:doc "constant that represents when a car 'in front'; i.e., there are no other cars on the road ahead of it"}
	IN-FRONT (. Integer MAX_VALUE))

(defstruct behavior :accel-dist :decel-dist)
(defstruct car :name :position :length :speed :behavior)

(defn 
	#^{:doc "decrements the cars speed; does not modify the car, just returns the new speed"}
	brake [dec {:keys [speed] :as car}]
	(- speed dec))

(defn
 	#^{:doc "increments the cars speed; does not modify the car, just returns the new speed"}
	accel [accel {:keys [speed] :as car}]
	(+ speed accel))

(defn 
	#^{:doc "checks the cars distance versus its behavior and changes the speed accordingly; does not modify the car, just returns the new speed"}
	speed-change [{:keys [behavior speed] :as car} distance]
	(cond 
		(< distance (:decel-dist behavior)) (brake 1 car)
		(> distance (:accel-dist behavior)) (accel 1 car)
		:else speed))

(defn
	#^{:doc "checks alls cars against their following distances and changes their speeds accordingly; does not modify the car, just returns the new speed"}
	all-speed-changes [cars distances]
	(map speed-change cars distances))
