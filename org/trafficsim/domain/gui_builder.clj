(ns org.trafficsim.domain.gui-builder
	(:use )
	(:import (java.awt Color) (java.awt.event ActionListener) (javax.swing JFrame JPanel JButton Timer) (java.lang System))
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

(defn draw-top-tire [graphics {:keys [head-position length]}]
	(.setColor graphics (Color/YELLOW))
	(.fillRect graphics (+ ROAD_LEFT head-position 2) (+ ROAD_TOP CAR_TIRE_ROAD_OFFSET) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT)
	(.fillRect graphics (+ ROAD_LEFT head-position (- length (+ CAR_TIRE_LENGTH 2))) (+ ROAD_TOP CAR_TIRE_ROAD_OFFSET) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT))

(defn draw-bottom-tire [graphics {:keys [head-position length]}]
	(.setColor graphics (Color/YELLOW))
	(.fillRect graphics (+ ROAD_LEFT head-position 2) (+ ROAD_TOP CAR_BODY_ROAD_OFFSET CAR_BODY_HEIGHT) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT)
	(.fillRect graphics (+ ROAD_LEFT head-position (- length (+ CAR_TIRE_LENGTH 2))) (+ ROAD_TOP CAR_BODY_ROAD_OFFSET  CAR_BODY_HEIGHT) CAR_TIRE_LENGTH CAR_TIRE_HEIGHT))

(defn draw-car [graphics {:keys [head-position length] :as car}]
	(.setColor graphics (Color/WHITE))
	(.fillRect graphics (+ ROAD_LEFT head-position) (+ ROAD_TOP CAR_BODY_ROAD_OFFSET) length CAR_BODY_HEIGHT)
	(draw-top-tire graphics car)
	(draw-bottom-tire graphics car))

(def curr-tick (atom 0))

(defn inc-tick [curr-tick accumulator]
	(let [curr-count (count ((:get accumulator)))]
		(if (< @curr-tick (- curr-count 1))
			(swap! curr-tick inc))))

(defn dec-tick [curr-tick]
	(if (> @curr-tick 0)
		(swap! curr-tick dec)))

(defn add-action-listener [component f]
	(.addActionListener component 
		(proxy[ActionListener]
			[]
			(actionPerformed [e] (f)))))

(defn create-panel [accumulator]
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
			(inc-tick curr-tick accumulator)
			(.repaint this))))
		
(defn add-button [panel name callback]
	(let [button (new JButton name)]
		(add-action-listener button callback)
		(.add panel button)))

(defn create-gui [accumulator]
	(def panel (create-panel accumulator))
	(def timer (Timer. 250 panel))
	(add-button panel "<-" (fn [] (do (dec-tick curr-tick) (. panel repaint))))
	(add-button panel "Play" #(.start timer))
	(add-button panel "->"(fn [] (do (inc-tick curr-tick accumulator) (. panel repaint))))
	(add-button panel "Stop" #(.stop timer))
	(add-button panel "Reset" (fn []
		(do
			(reset! curr-tick 0)
			(. panel repaint))))
	
	(doto (JFrame. "Traffic Simulation")
		(.add panel)
		(.setResizable false)
		(.setSize 1200, 400)
		(.setVisible true)
		(.setDefaultCloseOperation (JFrame/EXIT_ON_CLOSE))))

