(ns org.trafficsim.domain.car)

(def 
	#^{:doc "constant that represents when a car 'in front'; i.e., there are no other cars on the road ahead of it"}
	IN-FRONT (. Integer MAX_VALUE))

(defstruct observable-entities :cars) ;add other types of observable entities (e.g. traffic lights) as we implement them
(defstruct behavior :accel-dist :decel-dist)
(defstruct car :name :head-position :length :speed :behavior)

(defn default-car []
	(struct car :a 1 0 1 (struct behavior 10 5)))

(defn car-with 
	([key value]
		(assoc (default-car) key value))
	([key1 value1 key2 value2]
		(assoc (car-with key1 value1) key2 value2))
	([key1 value1 key2 value2 key3 value3]
		(assoc (car-with key1 value1 key2 value2) key3 value3)))

(defn
 	#^{:doc "sorts a vector of cars by position"}
	sort-cars-by-position [cars]
 	(sort #(- (:head-position %2) (:head-position %1)) cars))

(defn 
	get-car-tail-position [{:keys [head-position length]}]
	(- head-position length))

;Distance between cars can be 0 if my head-position is 1 and you're head-position is 2 and length is 1 then the distance is 0
(defn distance-between [car1 car2]
	(- (get-car-tail-position car2) (:head-position car1)))

(defn 
	get-new-slower-speed [dec {:keys [speed] :as car}]
	(- speed dec))

(defn
	get-new-faster-speed [accel {:keys [speed] :as car}]
	(+ speed accel))

(defn 
	get-new-target-speed [{:keys [behavior speed] :as car} observable-entities]
	(cond 
		(< observable-entities (:decel-dist behavior)) (get-new-slower-speed 1 car)
		(> observable-entities (:accel-dist behavior)) (get-new-faster-speed 1 car)
		:else speed))

(defn get-car-infront-of [car other-cars]
	(last (sort-cars-by-position (filter #(< (:head-position car) (get-car-tail-position %)) other-cars))))

(defn get-car-behind [car other-cars]
	(first (sort-cars-by-position (filter #(> (get-car-tail-position car) (:head-position %)) other-cars))))

(defn get-cars-around [car other-cars]
	(filter #(not (nil? %)) (seq [(get-car-behind car other-cars) (get-car-infront-of car other-cars)])))

(defn 
	get-new-target-speed-2 [{:keys [behavior speed position] :as car} observable-entities]
	(cond 
		(empty? (:cars observable-entities)) (get-new-faster-speed 1 car)
		(< (distance-between car (get-car-infront-of car (:cars observable-entities))) (:decel-dist behavior)) (get-new-slower-speed 1 car)
		(> (distance-between car (get-car-infront-of car (:cars observable-entities))) (:accel-dist behavior)) (get-new-faster-speed 1 car)
		:else speed))


(defn 
	apply-new-speed [car new-speed]
	(assoc car :speed new-speed))


