(ns geopressure.chooser

  (:import [javax.swing JFileChooser JButton JFrame])
  (:impor  [javax.swing.filechooser FileNameExtensionFilter])
  (:use [incanter core io excel datasets])
  (:gen-class))


(defmacro on-action [component event & body]
  `(. ~component addActionListener
      (proxy [java.awt.event.ActionListener] []
        (actionPerformed [~event] ~@body))))


(defn get-file [ ]
  (let [extFilter (FileNameExtensionFilter. "Text File" (into-array  ["txt" "md" "xls" "csv"]))
        filechooser (JFileChooser. "C:/")
        dummy (.setFileFilter filechooser extFilter)
        retval (.showOpenDialog filechooser nil) ]
    (if (= retval JFileChooser/APPROVE_OPTION)
      (do
        (def drlg-data(read-dataset (.getSelectedFile filechooser) :header true))
        )
      "")))

