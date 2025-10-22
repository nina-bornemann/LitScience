import "./Home.css"

export default function Home() {

    return (
        <div className={"home"}>
            <div className={"home-title"}>
                <h1>🧪    Welcome to LitScience! </h1>
                <img src={"/src/assets/Smiley_Cell.png"} alt={"Cell-Smiley-Logo"} className={"smiley"}/>
            </div>
            <div>
                <h2> ✨ What you can do here: </h2>
                <p>📚 Build your personal paper collection</p>
                <p>🔍 Find and import papers online by DOI</p>
                <p>🧠 Add notes and tags for every paper</p>
                <p>🗑 Clean up with one-click delete</p>
                <p>🧾 (Coming soon) Generate AI-powered summaries and reports</p>

                <p> Stay curious, stay organized — and let LitScience take care of the messy part of science reading 💡</p>

            </div>
        </div>
    )
}