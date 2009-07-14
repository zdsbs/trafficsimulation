(ns org.trafficsim.domain.world-gui
	(:use org.trafficsim.domain.world)
	(:import (java.awt Color) (java.awt.event ActionListener KeyEvent KeyListener) (javax.swing JFrame JPanel Timer))
)

; Create and configure a JPanel.
(def panel
  ; Create a proxy object that extends JPanel and
  ; implements both ActionListener and KeyListener.
  (proxy [JPanel]
    [] ; arguments to the superclass constructor (none here)

    ; What follows are implementations of the interface methods.
    
    (paintComponent [graphics]
      (proxy-super paintComponent graphics)

      ; Paint the apple using the paint function defined earlier.
	  (.setColor graphics (Color. 0 0 0))
	  (.drawRect graphics 1 20 (road :length) (road :width))
    )
  )
)


; Create a JFrame and add the JPanel to it.
(doto (JFrame. "Road")
  (.add panel)
  (.setResizable false)
  (.setSize 1100 100)
  (.setVisible true)
)

; Fire an ActionEvent on the JPanel every 75 ms.
;(.start (Timer. 75 panel))
