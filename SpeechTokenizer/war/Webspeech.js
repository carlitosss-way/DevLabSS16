/**
 * In dieser Klasse wird der Google Webspeech Service angesprochen, welche durch den StartButton aufgerufen wird.
 * @author Verena Hofmann
 */


var sttResult = '';	
var recognizing = false;
var ignore_onend;
/**
 * Die Methode <code>doPoll()</code> führt das Polling zum Server durch. 
 * Diese Methode wird in der Klasse InterfaceTomcat.js ausgeführt.
 */
doPoll();

	if (!('webkitSpeechRecognition' in window)) { 
		
			/**Hier wird geprüft, ob der Browser die Web Speech API unterstützt. 
			Falls nicht wird er mit Hilfe der Methode <code>update()</code> aufgefordert den Browser upzudaten.
			*/
			upgrade ();
			/**
			 * Falls nicht wird else ausgeführt
			 */
			
		} else {
			/**
			 * Erlaubt der Seite Zugriff auf das Mikrofon - Das webkitSpeechRecognition() Objekt 
			 * stellt das Google Webspeech Interface bereit und definiert Eventhandler.
			 * Über das @param recognition wird der gesamte Recognition-Prozess verwaltet
			 */
			var recognition = new webkitSpeechRecognition();
			
  			/* Continous: true = User kann Pause einlegen, bei false wird sofort geschickt sobald pause*/
  			recognition.continuous = true;
	
			/*Listening (capturing voice from audio input) started.*/
			recognition.onstart = function() {
    			recognizing = true;   
    			/**Mit dieser Methode wird der Status des Mikrofons ausgelesen 
    			* und falls es aktiv ist grün "Mic aktiv" dargestellt. 
    			* */
    			var micstatuselement = document.getElementById('micstatus');
    			/**
    			 * Outcomment the next two lines for tests on local server.
    			 * You need the two lines just for App Engine.
    			 */
    			 micstatuselement.style.color='green';
    			 micstatuselement.innerHTML='Mic aktiv';
  			};
  			
  			/**Diese Funktion <code>onend()</code> wird aufgerufen, wenn die Aufnahme beendet wurde. 
  			 * Dabei wird das Attribut @param recognizing auf false gesetzt.
  			*/
  			recognition.onend = function() {
    			recognizing = false;
    			if (ignore_onend) {
      				return;
    			}
    			/*Mit dieser Methode wird der Status des Mic ausgelesen und falls es aktiv "Mic inaktiv" ausgegeben.*/
    			 var micstatuselement = document.getElementById('micstatus');
    			    	  		
    			 /**
    			 * Outcomment the next two lines for tests on local server.
    			 * You need the two lines just for App Engine.
    			 */
    			 micstatuselement.removeAttribute('style');
    			 micstatuselement.innerHTML='Mic inaktiv';
    			 
  	};
  		
  	/**
  	 * Die Methode <code>onresult()</code> durchläuft mit einer For-Schleife des erzeugte Result des Audios. 
  	 * @param sttResult wird anschließend durch <code>transcript</code> in einen Text umgewandelt.
  	 * Durch den Aufruf der Methode <code>Token()</code> wird diese in der Klasse Token.js aufgerufen.
  	 * Anschließend wird der Text in HTML übersetzt und in angezeigt.
  	 */
  	
  	recognition.onresult = function(event) {
    			for (var i = event.resultIndex; i < event.results.length; ++i) {
      				if (event.results[i].isFinal) {
        				sttResult = event.results[i][0].transcript;
      				}
      				
      				//Aufruf der Token Funktion in Token.js      				
      				Token();
    			}
    			/*linebreak für neuen Paragraphen, wird dann in HTML übersetzt*/
    			sttResult = capitalize(sttResult);
    			
    			/*final_span Anzeige in HTML*/
    			final_span.innerHTML = linebreak(sttResult);
  	};
}
  				
  			
/**
 * Die Methode <code>startButton(event)</code> wird nach Drücken des Start-Buttons aufgerufen.
 * Dabei wird, falls die Aufnahme bereits läuft, diese zuerst gestoppt. Falls es gestartet wird,
 * gibt die Console "Service gestartet" aus. Danach wird das @param sttResult geleert und die 
 * durch die Methode <code>start()</code> 
 */
function startButton(event) {	
	if (recognizing) {
    	recognition.stop();
    	return;
  	}
	console.log("Service gestartet");
	sttResult = '';
	/*aktiviert den Speech Recognizer und ruft onstart Eventhandler auf*/
	recognition.lang = "en-US";
	recognition.start();
	ignore_onend = false;
	final_span.innerHTML = '';
}

/** Durch die Methoden <code>linebreak(sttResult)</code> und <code>capatilize(sttResult)</code> wird Text, 
 * also das sttResult in der App eingeblendet.
 */

function linebreak(sttResult) {
	return sttResult;
}

function capitalize(sttResult) {
	return sttResult;
}
