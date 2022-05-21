import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NetListener {

    public static void main(String[] args) throws IOException {

        String localip = "local_ip";

        String cmd = "ping " + localip + " -t";

        Process process = Runtime.getRuntime().exec(cmd);

        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;

        boolean hasConnected = false;

        while ((s = br.readLine()) != null) {

            if (s.toLowerCase().contains("reply from " + localip) && !hasConnected) {
                Voice voice = VoiceManager.getInstance().getVoice("kevin");
                if (voice != null) {
                    voice.allocate();
                    voice.speak("Hassans device has connected to the internet");
                    hasConnected = true;
                }
            }

            if (s.toLowerCase().contains("request timed out") && hasConnected) {
                System.out.println("Device has disconnected.");
                hasConnected = false;
            }

            if (!hasConnected)
                System.out.println("Waiting for connection...");

        }


    }

}
