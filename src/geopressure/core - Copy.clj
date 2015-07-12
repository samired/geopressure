(ns geopressure-clj.core
  (:use [incanter core io charts excel datasets stats])
  (:import [org.jfree.chart ChartPanel])
  (:import [java.awt Component Dimension])
  (:import [javax.swing JFrame JPanel JButton JTextField JInternalFrame])
  (:import [javax.swing JFileChooser JButton JFrame])
  (:import  [javax.swing.filechooser FileNameExtensionFilter])
  (:import [java.awt GridLayout])
  
  (:gen-class))

;; Scientific analysis functions grouped together in next part

(defn d [R N D W]
  "d = log10(R/60N)/log10(12W/106D) where : R=ROP (ft/hr) N=RPM (rev/min) W=WOB (lbs) D=bit size (ins)"
  (/ (Math/log10 (/ R (* 60 N)))
     (Math/log10 (/ (* 12 W) (* 1000 D)))))


(defn dxc [MW1 MW2 R N D W]
  (* (d R N D W) (/ MW1 MW2)))


(defn c1c2 [C1 C2] (if (zero? C2) 0 (/ C1 C2)))
(def DEPTH (atom ()))
(def ROP (atom ()))
(def RPM (atom ()))
(def WOB (atom ()))
(def BIT (atom ()))
(def ROP (atom ()))
(def Dx (map #(d %1 %2 %3 %4) ROP RPM BIT WOB))

(def C1 (atom ()))
(def C2 (atom ()))


(defn show-component
  ([^Component c1 c2 c3]
    "Utility Function to display any Java component in a frame"
    (let [^JFrame frame (JFrame. "Test Window")]
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
  (def drlg-data (read-dataset "C:\\Dropbox\\Drafts\\geopressure\\test.las" :header true))
  (def gas-data (read-dataset "C:\\Dropbox\\Drafts\\geopressure\\gas.las" :header true))   
  (swap! DEPTH conj ($ 0 drlg-data))
  (swap! ROP conj ($ 9 drlg-data))
  (swap! RPM conj ($ 10 drlg-data))
  (swap! WOB conj ($ 14 drlg-data))
  (swap! BIT conj ($ 2 drlg-data))

  (def Dx (map #(d %1 %2 %3 %4) ROP RPM BIT WOB))
  
  (swap! C1 conj ($ 1 gas-data))
  (swap! C2 conj ($ 4 gas-data))
  (def C1C2 (map #(c1c2 %1 %2) C1 C2))
  
  
  (def plot1 (xy-plot DEPTH Dx :vertical false))
  (def plot2 (area-chart DEPTH Dx ))
  (def plot3 (xy-plot DEPTH C1C2))
  
  (def lm1 (linear-model Dx DEPTH))
  (add-lines plot1 DEPTH (:fitted lm1))
  
  (def lm2 (linear-model Dx DEPTH :intercept false))
  (add-lines plot1 DEPTH (:fitted lm2))
      
  (show-component (ChartPanel. plot1)
                  (ChartPanel. plot2)
                  (ChartPanel. plot3)))