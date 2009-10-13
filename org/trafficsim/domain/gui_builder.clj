(ns org.trafficsim.domain.gui-builder
	(:use )
	(:import (java.awt Color) (java.awt.event ActionListener) (javax.swing JFrame JPanel Timer) (java.lang System))
)

(def ROAD_LEFT 100)
(def ROAD_TOP 150)
(def ROAD_HEIGHT 30)
(def ROAD_LENGTH 1000)
(def CAR_TIRE_LENGTH 4)
(def CAR_TIRE_HEIGHT 2)
(def CAR_TIRE_ROAD_OFFSET 3)
(def CAR_BODY_ROAD_OFFSET (+ CAR_TIRE_ROAD_OFFSET CAR_TIRE_HEIGHT))
(def CAR_BODY_HEIGHT (- ROAD_HEIGHT (* 2 CAR_BODY_ROAD_OFFSET)))

(defn draw-road [graphics]
	(.setColor graphics (Color/BLACK))
	(.fillRect graphics ROAD_LEFT 150 ROAD_LENGTH ROAD_HEIGHT))

(defn draw-top-tire [graphics {:keys [position length]}]
	(.setColor graphics (Color/YELLOW))
	(.fillRect graphics (+ ROAD_LEFT position 2) (+ ROAD_TOP CAR_TIRE_ROAD_OFFSET) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT)
	(.fillRect graphics (+ ROAD_LEFT position (- length (+ CAR_TIRE_LENGTH 2))) (+ ROAD_TOP CAR_TIRE_ROAD_OFFSET) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT))

(defn draw-bottom-tire [graphics {:keys [position length]}]
	(.setColor graphics (Color/YELLOW))
	(.fillRect graphics (+ ROAD_LEFT position 2) (+ ROAD_TOP CAR_BODY_ROAD_OFFSET CAR_BODY_HEIGHT) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT)
	(.fillRect graphics (+ ROAD_LEFT position (- length (+ CAR_TIRE_LENGTH 2))) (+ ROAD_TOP CAR_BODY_ROAD_OFFSET  CAR_BODY_HEIGHT) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT))

(defn draw-car [graphics {:keys [position length] :as car}]
	(.setColor graphics (Color/WHITE))
	(.fillRect graphics (+ ROAD_LEFT position) (+ ROAD_TOP CAR_BODY_ROAD_OFFSET) length CAR_BODY_HEIGHT)
	(draw-top-tire graphics car)
	(draw-bottom-tire graphics car))

(def curr-tick (atom 0))

(defn create-gui [accumulator]
	(def panel
		(proxy[JPanel ActionListener]
			[] ;arguments to the superclass constrctor (none here)
		
			(paintComponent [graphics]
				(proxy-super paintComponent graphics)
			
				;paint the road
				(draw-road graphics)
			
				;paint the cars
				(doseq [car (((accumulator :get)) @curr-tick)] (draw-car graphics car))
			)
			
			(actionPerformed [e]
				(swap! curr-tick inc)
				(.repaint this))))
	
	(doto (JFrame. "Traffic Simulation")
		(.add panel)
		(.setResizable false)
		(.setSize 1200, 400)
		(.setVisible true)
		(.setDefaultCloseOperation (JFrame/EXIT_ON_CLOSE)))

	(.start (Timer. 250 panel)))

