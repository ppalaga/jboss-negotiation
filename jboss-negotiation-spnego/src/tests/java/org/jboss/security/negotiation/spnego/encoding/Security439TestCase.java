package org.jboss.security.negotiation.spnego.encoding;

import java.io.ByteArrayInputStream;

import junit.framework.TestCase;

import org.jboss.security.negotiation.MessageFactory;
import org.jboss.security.negotiation.NegotiationMessage;
import org.jboss.security.negotiation.common.DebugHelper;
import org.jboss.util.Base64;

public class Security439TestCase extends TestCase
{

   private static final String ORIGINAL_MESSAGE = "oYIEuTCCBLWiggSxBIIErWCCBKkGCSqGSIb3EgECAgEAboIEmDCCBJSgAwIBBaEDAgEOogcDBQAAAAAAo4IDvGGCA7gwggO0oAMCAQWhFBsSVk0xMzdET01BSU4uR1NTTEFCoh0wG6ADAgECoRQwEhsESFRUUBsKdGVzdHNlcnZlcqOCA3YwggNyoAMCAQOhAwIBCKKCA2QEggNgKkP9UsEh8N5+l9DLqJxddNJy3TVIRQ2CX6KfMzK97vVKiTPkUbbqV+zI3ALjVfCMtWhHE8CeAjJ3BQQYjYJhbyFs32Y7e4g/btcRUutI6q4eRw3KwZGOWJBWZhc2XVAFWN9lMbq3RvnjEAyD8HIcAxk0iXVBGPRn5n0qKDPdsedWk+lUtc8zVxaf29wsEnNg1MjoXMlZj6z/odwbfrQGUC+A6MHBIlf4h1SzdJtVYyEZdvPGlBRX96nCXyi+NVgSCAH8JEjTbO2KssZPdrvhEgbkUraR+hef1BL4R1/wosB7jhSVisS8S1Xf8cIkYDHuh/c6OPFEw6EV9eOEKuXZHOsV39rXaRUYQRVIowhrvFcgHB3Ev1KCs8sE5QZsrMpX10gqsuAQhRhPav8WeNq3cB6VUWDWQSIvlux8jsI7J4vtm2WDgZFTXkyOWy5HcaR7/kM/pPq2cK0e51tttYeiXutc/Hz8iy6vXb21D/lG+9LOZxgPbXNMjEgwwzu45q1sA5oSeE4M0Vj+XJlDyne8vCRKjEgQCyGr5keDGhOdxXr89OJvQaAJJFSbZg3ku+hyPI6gsgburOWh8qQ3/dScYNYwU3k4wfqauGJkyoM8F57k4g79bbIf+CuxRE+PcTIjhpMdMQ0Nsbn+xWsSP7mleTkQtcuGQBYSMxva1w1b/+9bObtax/PXlLIlnPL8n6RC8IbfMTeCBW3U+5g3FlUWRbDZ2jjwRbRkVhmFFDMpAiEzxXOLvlPOknPEhD91dXaOxbn6n6mvMdVqwF6/C7lpmS6PrEsCK4lTWZOvAsEjyhCqWaCesB9jG5bAXNIMm3Wtk+UvGcjvoEaHScX/KOp9Uk+dHzmqRTcU4+Xrs76odUyQCs09YePG1zukGBvOuqYkZ+8vPzAApkjzy7KPI/Bc9yU1i1ish3FJgvv4AF1+EybNjtVRKCB4z4ZhDuX+yIwiWtp9u1I/bvUFRLgyrPyb/cH3k9uWSVY14SgHyCAfmnAr99GIXaQmLRTWr5YTXZ5yszbRqHf06jHiA6KLH8EDlYL+kMuYB9O4Hq5DsXxbCM1vBqQXZKoerpk0FSxbOBxRngwdHBd2lpvu3AZjX+M1QpPc/eQl3y4PaYHEgDdrSqgVFGgMAVdo+rpDCwscxs+opIG+MIG7oAMCAQOigbMEgbDpiIZo813QlBcC79Su+BgNbx7m5U2l7wm1HHLiwSX1uCaEAtEjw9o+6NKr/Ca8dDUaa278uqeVaIOXYRMPKxO1p/9ObHs+Hx+wfcy0TW7dp9LptX9Mwi/HaXRk5pYkXPBIa2n8D5oO+EarBB29y48zQwj+bwDoADiFv1cMOvN48m+7qLEicc0fdxvAHfeGPiWdMx0genvWsK7lSASeRU1xC22qjd1+Qv05MuwXWKEbyA==";

   private static final String ISSUE_MESSAGE = "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";

   public void testDecode_OriginalMessage() throws Exception
   {
      decode(ORIGINAL_MESSAGE);
   }

   public void testDecode_IssueMessage() throws Exception
   {
      decode(ISSUE_MESSAGE);
   }

   private void decode(final String message) throws Exception
   {
      byte[] authToken = Base64.decode(message);
      String hex = DebugHelper.convertToHex(authToken);

      ByteArrayInputStream authTokenIS = new ByteArrayInputStream(authToken);

      MessageFactory mf = MessageFactory.newInstance();
      NegotiationMessage requestMessage = mf.createMessage(authTokenIS);
   }

}
