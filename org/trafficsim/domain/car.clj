(ns org.trafficsim.domain.car)

(def IN-FRONT (. Integer MAX_VALUE))

(defstruct behavior :accel-dist :decel-dist)
(defstruct car :name :position :length :speed :behavior)

(defn brake [dec {:keys [speed] :as car}]
	(- speed dec))

(defn accel [accel {:keys [speed] :as car}]
	(+ speed accel))

(defn speed-change [{:keys [behavior speed] :as car} distance]
	(cond 
		(< distance (:decel-dist behavior)) (brake 1 car)
		(> distance (:accel-dist behavior)) (accel 1 car)
		:else speed))

(defn all-speed-changes [cars distances]
	(map speed-change cars distances))
