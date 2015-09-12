(ns geopressure.core
  (:use [incanter core io charts excel datasets stats])
  (:import [org.jfree.chart ChartPanel])
  (:import [java.awt Component Dimension])
  (:import [javax.swing JFrame JPanel JButton JTextField JInternalFrame])
  (:import [javax.swing JFileChooser JButton JFrame])
  (:import [javax.swing.filechooser FileNameExtensionFilter])
  (:import [java.awt GridLayout]))

;; Scientific analysis functions grouped together in next part

(defn calculate-dx
  "d = log10(R/60N)/log10(12W/106D) where : R=ROP (ft/hr) N=RPM (rev/min) W=WOB (lbs) D=bit size (inch)"
  [R N D W]
  (/ (Math/log10 (/ R (* 60 N)))
     (Math/log10 (/ (* 12 W) (* 1000 D)))))


(defn calculate-dxc [MW1 MW2 R N D W]
  (* (calculate-dx R N D W) (/ MW1 MW2)))


(defn c1c2 "C1 devided by C2 for fluid type indication"
  [C1 C2]
    (if (zero? C2) 0 (/ C1 C2)))

(def drlg-data (read-dataset "/media/smir/Data/refs/geology/geomechanics/Case Study/RA-472/drilling-ascii.csv" :header true))
;(def gas-data (read-dataset "data\\gas.las" :header true))
(def DEPTH ($ 0 drlg-data))
(def ROP ($ 6 drlg-data))
(def RPM ($ 5 drlg-data))
(def WOB ($ 2 drlg-data))
(def BIT ($ 22 drlg-data))

(def Dx (map #(calculate-dx %1 %2 %3 %4) ROP RPM BIT WOB))

;(def C1 ($ :MUDGAS_C1 gas-data))
;(def C2 ($ :MUDGAS_C2 gas-data))
;(def C1C2 (map #(c1c2 %1 %2) C1 C2))


(def plot1 (xy-plot Dx DEPTH))
(def plot2 (area-chart Dx DEPTH))
;(def plot3 (xy-plot DEPTH C1C2))
;(def plot4 (xy-plot DEPTH C1C2))

(def lm1 (linear-model Dx DEPTH))
(add-lines plot1 DEPTH (:fitted lm1))

;(def lm2 (linear-model Dx DEPTH :intercept false))
;(add-lines plot1 DEPTH (:fitted lm2))

(defn show-component
  ([^Component c1 c2 c3]
     "Utility Function to display any Java component in a frame"
     (let [frame (JFrame. "Test Window")]
       (doto frame
         (.setDefaultCloseOperation JFrame/DISPOSE_ON_CLOSE)
         (.setLayout (new GridLayout 1 4 4 4))
         (.add c1)
         (.add c2)
         (.add c3)
         (.setSize (Dimension.  640 480))
         (.setExtendedState (. JFrame MAXIMIZED_BOTH))
         (.setVisible true)))))

(defn -main []

  (show-component
;   (doto (JPanel.) (.add (JTextField.)) (.add (JButton. "test")) )
   (ChartPanel. plot1)
   (ChartPanel. plot2)
   (ChartPanel. plot1)
   ))
