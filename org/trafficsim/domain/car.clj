(ns org.trafficsim.domain.car)

(def IN-FRONT (. Integer MAX_VALUE))

(defstruct behavior :accel-dist :decel-dist :target-speed)
(defstruct car :length :speed :position :acc-behavior :braking-behavior :max-speed-behavior)

(defn brake [dec name cur-speed]
	[name (- cur-speed dec)]
)

(defn accel [acc name cur-speed]
	[name (+ cur-speed acc)])

(defn speed-change [[behavior name speed distance]]
	(cond 
		(< distance (:decel-dist behavior)) (brake 1 name speed)
		(> distance (:accel-dist behavior)) (accel 1 name speed)
		:else [name speed]
		))

(defn collect-all-new-speed-changes [braking-data] 
	(map speed-change braking-data))