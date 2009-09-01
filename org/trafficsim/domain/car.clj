(ns org.trafficsim.domain.car)

(def 
	#^{:doc "constant that represents when a car 'in front'; i.e., there are no other cars on the road ahead of it"}
	IN-FRONT (. Integer MAX_VALUE))

(defstruct behavior :accel-dist :decel-dist)
(defstruct car :name :position :length :speed :behavior)

(defn default-car []
	(struct car :a 1 0 1 (struct behavior 10 5)))

(defn car-with 
	([key value]
		(assoc (default-car) key value))
	([key1 value1 key2 value2]
		(assoc (car-with key1 value1) key2 value2)))

(defn 
	get-new-slower-speed [dec {:keys [speed] :as car}]
	(- speed dec))

(defn
	get-new-faster-speed [accel {:keys [speed] :as car}]
	(+ speed accel))

(defn 
	get-new-target-speed [{:keys [behavior speed] :as car} distance]
	(cond 
		(< distance (:decel-dist behavior)) (get-new-slower-speed 1 car)
		(> distance (:accel-dist behavior)) (get-new-faster-speed 1 car)
		:else speed))

(defn 
	apply-new-speed [car new-speed]
	(assoc car :speed new-speed))

(defn 
	move-car [car]
	(assoc car :position (+ (car :position) (car :speed))))

(defn 
	get-car-tail-position [{:keys [position length]}]
	(- position length))
