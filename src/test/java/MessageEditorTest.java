import gov.nist.hit.core.domain.Message;
import gov.nist.hit.core.xml.domain.XMLTestContext;
import gov.nist.hit.impl.XMLMessageEditor;
import gov.nist.hit.impl.XMLMessageParser;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mcl1 on 1/19/16.
 */
public class MessageEditorTest {

    String xmlMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" +
            "<Message xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" version=\"010\" release=\"006\" xmlns=\"http://www.ncpdp.org/schema/SCRIPT\">" +
            "    <Header>" +
            "        <To Qualifier=\"P\">RECIPIENT_ID</To>" +
            "        <From Qualifier=\"D\">SENDER_ID</From>" +
            "        <MessageID>90927</MessageID>" +
            "        <SentTime>2015-11-20T14:15:23</SentTime>" +
            "        <PrescriberOrderNumber>ORDMU201</PrescriberOrderNumber>" +
            "    </Header>" +
            "    <Header>" +
            "        <To Qualifier=\"P\">RECIPIENT_ID</To>" +
            "        <From Qualifier=\"D\">SENDER_ID</From>" +
            "        <MessageID>ABCDEF</MessageID>" +
            "        <SentTime>2015-11-20T14:15:23</SentTime>" +
            "        <PrescriberOrderNumber>ORDMU201</PrescriberOrderNumber>" +
            "    </Header>" +
            "    <Body>" +
            "        <NewRx>" +
            "            <Pharmacy>" +
            "                <Identification>" +
            "                    <NPI>3030000003</NPI>" +
            "                    <NCPDPID>1629900</NCPDPID>" +
            "                </Identification>" +
            "                <StoreName>Mail Order Pharmacy 10.6MU NOCS</StoreName>" +
            "                <Address>" +
            "                    <AddressLine1>1629-90 Supply Ln</AddressLine1>" +
            "                    <City>Saint Louis</City>" +
            "                    <State>MO</State>" +
            "                    <ZipCode>63105</ZipCode>" +
            "                </Address>" +
            "                <CommunicationNumbers>" +
            "                    <Communication>" +
            "                        <Number>3145553142</Number>" +
            "                        <Qualifier>TE</Qualifier>" +
            "                    </Communication>" +
            "                </CommunicationNumbers>" +
            "            </Pharmacy>" +
            "            <Prescriber>" +
            "                <Identification>" +
            "                    <DEANumber>FF1234567</DEANumber>" +
            "                    <NPI>1619967999</NPI>" +
            "                </Identification>" +
            "                <ClinicName>Clinic One</ClinicName>" +
            "                <Name>" +
            "                    <LastName>MacClare</LastName>" +
            "                    <FirstName>Susan</FirstName>" +
            "                </Name>" +
            "                <Address>" +
            "                    <AddressLine1>10105 Trailblazer Ct</AddressLine1>" +
            "                    <City>Portland</City>" +
            "                    <State>OR</State>" +
            "                    <ZipCode>97215</ZipCode>" +
            "                </Address>" +
            "                <CommunicationNumbers>" +
            "                    <Communication>" +
            "                        <Number>5035552233</Number>" +
            "                        <Qualifier>TE</Qualifier>" +
            "                    </Communication>" +
            "                </CommunicationNumbers>" +
            "            </Prescriber>" +
            "            <Patient>" +
            "                <Identification>" +
            "                    <MedicalRecordIdentificationNumberEHR>Patient1</MedicalRecordIdentificationNumberEHR>" +
            "                </Identification>" +
            "                <Name>" +
            "                    <LastName>Biscayne</LastName>" +
            "                    <FirstName>Sophia</FirstName>" +
            "                </Name>" +
            "                <Gender>F</Gender>" +
            "                <DateOfBirth>" +
            "                    <Date>1957-03-21</Date>" +
            "                </DateOfBirth>" +
            "                <Address>" +
            "                    <AddressLine1>991 Monroe Avenue</AddressLine1>" +
            "                    <City>Port Charlotte</City>" +
            "                    <State>FL</State>" +
            "                    <ZipCode>33952</ZipCode>" +
            "                </Address>" +
            "                <CommunicationNumbers>" +
            "                    <Communication>" +
            "                        <Number>9415551223</Number>" +
            "                        <Qualifier>TE</Qualifier>" +
            "                    </Communication>" +
            "                </CommunicationNumbers>" +
            "            </Patient>" +
            "            <MedicationPrescribed>" +
            "                <DrugDescription>Procardia XL 30 MG 24 HR Extended Release Oral Tablet</DrugDescription>" +
            "                <DrugCoded>" +
            "                    <ProductCode>00069265041</ProductCode>" +
            "                    <ProductCodeQualifier>ND</ProductCodeQualifier>" +
            "                    <Strength>30</Strength>" +
            "                    <DrugDBCode>207772</DrugDBCode>" +
            "                    <DrugDBCodeQualifier>SBD</DrugDBCodeQualifier>" +
            "                    <FormSourceCode>AA</FormSourceCode>" +
            "                    <FormCode>C42927</FormCode>" +
            "                    <StrengthSourceCode>AB</StrengthSourceCode>" +
            "                    <StrengthCode>C28253</StrengthCode>" +
            "                </DrugCoded>" +
            "                <Quantity>" +
            "                    <Value>53</Value>" +
            "                    <CodeListQualifier>38</CodeListQualifier>" +
            "                    <UnitSourceCode>AC</UnitSourceCode>" +
            "                    <PotencyUnitCode>C48542</PotencyUnitCode>" +
            "                </Quantity>" +
            "                <DaysSupply>30</DaysSupply>" +
            "                <Directions>Take 1 tablet a day by mouth for seven days, then take 2 tablets by mouth once a day.</Directions>" +
            "                <Refills>" +
            "                    <Qualifier>R</Qualifier>" +
            "                    <Value>0</Value>" +
            "                </Refills>" +
            "                <Substitutions>1</Substitutions>" +
            "                <WrittenDate>" +
            "                    <Date>2015-11-20</Date>" +
            "                </WrittenDate>" +
            "                <LastFillDate>" +
            "                    <Date>2015-10-30</Date>" +
            "                </LastFillDate>" +
            "                <Diagnosis>" +
            "                    <ClinicalInformationQualifier>1</ClinicalInformationQualifier>" +
            "                    <Primary>" +
            "                        <Qualifier>ABF</Qualifier>" +
            "                        <Value>I201</Value>" +
            "                    </Primary>" +
            "                </Diagnosis>" +
            "                <StructuredSIG>" +
            "                    <RepeatingSIG>" +
            "                        <SigSequencePositionNumber>1</SigSequencePositionNumber>" +
            "                        <MultipleSigModifier>THEN</MultipleSigModifier>" +
            "                    </RepeatingSIG>" +
            "                    <CodeSystem>" +
            "                        <SNOMEDVersion>20130731</SNOMEDVersion>" +
            "                        <FMTVersion>14.01d</FMTVersion>" +
            "                    </CodeSystem>" +
            "                    <FreeText>" +
            "                        <SigFreeTextStringIndicator>2</SigFreeTextStringIndicator>" +
            "                        <SigFreeText>Take 1 tablet a day by mouth for seven days, then take 2 tablets by mouth once a day.</SigFreeText>" +
            "                    </FreeText>" +
            "                    <Dose>" +
            "                        <DoseCompositeIndicator>1</DoseCompositeIndicator>" +
            "                        <DoseDeliveryMethodText>take</DoseDeliveryMethodText>" +
            "                        <DoseDeliveryMethodCodeQualifier>1</DoseDeliveryMethodCodeQualifier>" +
            "                        <DoseDeliveryMethodCode>419652001</DoseDeliveryMethodCode>" +
            "                        <DoseQuantity>1</DoseQuantity>" +
            "                        <DoseFormText>tablet</DoseFormText>" +
            "                        <DoseFormCodeQualifier>2</DoseFormCodeQualifier>" +
            "                        <DoseFormCode>C42998</DoseFormCode>" +
            "                    </Dose>" +
            "                    <RouteofAdministration>" +
            "                        <RouteofAdministrationText>by mouth</RouteofAdministrationText>" +
            "                        <RouteofAdministrationCodeQualifier>1</RouteofAdministrationCodeQualifier>" +
            "                        <RouteofAdministrationCode>26643006</RouteofAdministrationCode>" +
            "                    </RouteofAdministration>" +
            "                    <Timing>" +
            "                        <FrequencyNumericValue>1</FrequencyNumericValue>" +
            "                        <FrequencyUnitsText>day</FrequencyUnitsText>" +
            "                        <FrequencyUnitsCodeQualifier>1</FrequencyUnitsCodeQualifier>" +
            "                        <FrequencyUnitsCode>258703001</FrequencyUnitsCode>" +
            "                    </Timing>" +
            "                    <Duration>" +
            "                        <DurationNumericValue>7</DurationNumericValue>" +
            "                        <DurationText>day</DurationText>" +
            "                        <DurationTextCodeQualifier>1</DurationTextCodeQualifier>" +
            "                        <DurationTextCode>258703001</DurationTextCode>" +
            "                    </Duration>" +
            "                </StructuredSIG>" +
            "                <StructuredSIG>" +
            "                    <RepeatingSIG>" +
            "                        <SigSequencePositionNumber>2</SigSequencePositionNumber>" +
            "                    </RepeatingSIG>" +
            "                    <CodeSystem>" +
            "                        <SNOMEDVersion>20130731</SNOMEDVersion>" +
            "                        <FMTVersion>14.01d</FMTVersion>" +
            "                    </CodeSystem>" +
            "                    <FreeText>" +
            "                        <SigFreeTextStringIndicator>2</SigFreeTextStringIndicator>" +
            "                        <SigFreeText>Take 1 tablet a day by mouth for seven days, then take 2 tablets by mouth once a day.</SigFreeText>" +
            "                    </FreeText>" +
            "                    <Dose>" +
            "                        <DoseCompositeIndicator>1</DoseCompositeIndicator>" +
            "                        <DoseDeliveryMethodText>take</DoseDeliveryMethodText>" +
            "                        <DoseDeliveryMethodCodeQualifier>1</DoseDeliveryMethodCodeQualifier>" +
            "                        <DoseDeliveryMethodCode>419652001</DoseDeliveryMethodCode>" +
            "                        <DoseQuantity>2</DoseQuantity>" +
            "                        <DoseFormText>tablet</DoseFormText>" +
            "                        <DoseFormCodeQualifier>2</DoseFormCodeQualifier>" +
            "                        <DoseFormCode>C42998</DoseFormCode>" +
            "                    </Dose>" +
            "                    <RouteofAdministration>" +
            "                        <RouteofAdministrationText>by mouth</RouteofAdministrationText>" +
            "                        <RouteofAdministrationCodeQualifier>1</RouteofAdministrationCodeQualifier>" +
            "                        <RouteofAdministrationCode>26643006</RouteofAdministrationCode>" +
            "                    </RouteofAdministration>" +
            "                    <Timing>" +
            "                        <FrequencyNumericValue>1</FrequencyNumericValue>" +
            "                        <FrequencyUnitsText>day</FrequencyUnitsText>" +
            "                        <FrequencyUnitsCodeQualifier>1</FrequencyUnitsCodeQualifier>" +
            "                        <FrequencyUnitsCode>258703001</FrequencyUnitsCode>" +
            "                    </Timing>" +
            "                </StructuredSIG>" +
            "            </MedicationPrescribed>" +
            "        </NewRx>" +
            "    </Body>" +
            "</Message>";

    String ediMessage = "UNA:+./*'\n" +
            "UIB+UNOA:0++77777777:C+++SENDER_ID:D+RECIPIENT_ID:P+20151110:112544'\n" +
            "UIH+SCRIPT:010:006:CANRX+59338+ORDMU201'\n" +
            "REQ++1++++C'\n" +
            "PVD+PC+DD1234567:DH*1356602296:HPI+++Bates:Anna:::++Clinic One+15521-A Jackson Avenue:Long Island City:NY:11101+7185551212:TE'\n" +
            "PVD+P2+1120188:D3*4040000004:HPI+++++NYC Pharmacy 10.6MU+88 Park Street:Brooklyn:NY:11201+7185557181:TE'\n" +
            "PTT+1+19550108+Adirondack:Susanne:::+F+6532865:94+4447 Lake Forest Drive:La Grange (Dutchess):NY:12540+9795551216:TE'\n" +
            "DRU+P:Hydrochlorothiazide 50 MG Oral Tabl:00143125701:ND::50::197770:SCD:et:::AA:C42998:AB:C28253+:30:38:AC:C48542+:Take 1 tablet by mouth every morning.:+85:20151110:102*ZDS:30:804+0+R:2+1:I10:ABF'\n" +
            "SIG+0:+20130731:14.01d+2:Take one tablet by mouth every morning.+1:take:1:419652001::::1:tablet:2:C42998:+++by mouth:1:26643006:++morning:1:73775008:::::::::1:day:1:258703001::::::+:::+:::+::::::::::'\n" +
            "UIT+59338+9'\n" +
            "UIZ++1'";

    @Test
    public void testXMLEditor() throws ParserConfigurationException, IOException, SAXException, TransformerException {
        XMLMessageEditor xmlMessageEditor = new XMLMessageEditor();
        gov.nist.hit.core.domain.Message message = new Message();
        message.setContent(xmlMessage);
        HashMap<String,String> toBeReplaced = new HashMap<>();
        toBeReplaced.put("/Message/Header/MessageID","1234567890");
        toBeReplaced.put("NCPDPID","DLDRPZ");
        toBeReplaced.put("/Message/Body/NewRx/Pharmacy/Identification/NPI","QWERTYUIOP");
        try {
            String editedMessage = xmlMessageEditor.replaceInMessage(message,toBeReplaced,new XMLTestContext());
            XMLMessageParser xmlMessageParser = new XMLMessageParser();
            message.setContent(editedMessage);
            Map<String,String> data = xmlMessageParser.readInMessage(message, new ArrayList(toBeReplaced.keySet()), null);
            for(String key : toBeReplaced.keySet()){
                Assert.assertEquals(toBeReplaced.get(key), data.get(key));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        @Test
        public void testXMLParser()  {
                XMLMessageParser xmlMessageParser = new XMLMessageParser();
                gov.nist.hit.core.domain.Message message = new Message();
                message.setContent(xmlMessage);
                HashMap<String,String> toBeFoundWithValue = new HashMap<>();
                toBeFoundWithValue.put("/Message/Header/MessageID","90927");
                toBeFoundWithValue.put("NCPDPID","1629900");
                toBeFoundWithValue.put("/Message/Body/NewRx/Pharmacy/Identification/NPI","3030000003");
                try {
                        Map<String,String> data = xmlMessageParser.readInMessage(message, new ArrayList(toBeFoundWithValue.keySet()), null);
                        for(String key : toBeFoundWithValue.keySet()){
                                Assert.assertEquals(toBeFoundWithValue.get(key), data.get(key));
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

}
