import "./Impressum.css"

export default function Impressum() {

    return (
        <div className={"impressum-container"}>
            <h2> LEGAL NOTICE & PRIVACY </h2>
            <p className={"impressum"}>
                <b className={"impressum-titles"}>1. Contact / Impressum</b><br/>
                Nina Bornemann<br/>
                Fehrbelliner Straße 51<br/>
                10119 Berlin<br/>
                Germany<br/>
                nina.bornemann@freenet.de<br/>

                Responsible for content: Nina Bornemann<br/>

                <b className={"impressum-titles"}>2. Privacy Notice</b><br/>
                We take your data privacy seriously. This website collects only the data you voluntarily provide via GitHub login<br/>
                Collected data may include: Username, E-Mail, IP address.<br/>

                <b className={"impressum-titles"}>3. Purpose</b><br/>
                Your data is used only to respond to allow access to the website.<br/>

                <b className={"impressum-titles"}>4. Data Sharing</b><br/>
                Data is not shared with third parties unless legally required or with your consent.<br/>

                <b className={"impressum-titles"}>5. Your Rights</b><br/>
                You can request access, correction, deletion, or restriction of your personal data.<br/>
                Contact: nina.bornemann@freenet.de<br/>

                <b className={"impressum-titles"}>6. Updates</b><br/>
                We may update this notice to comply with legal requirements.<br/>
                Last updated: 12.11.2025<br/>

            </p>
        </div>
    )
}