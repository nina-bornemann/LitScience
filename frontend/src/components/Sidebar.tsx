import {useRef, useState} from "react";
import "./Sidebar.css";

export default function Sidebar() {
    const [isOpen, setIsOpen] = useState(false);
    const toggleRef = useRef<HTMLButtonElement>(null);

    return (
        <aside className={`sidebar ${isOpen ? "open" : "collapsed"}`}>
            <div className="sidebar-header">
                <a href="/" className="sidebar-title">
                    <i className="fa-solid fa-dna"></i>
                    {isOpen && <span>LitScience</span>}
                </a>
                <button ref={toggleRef} className="toggle-btn" onClick={() => {
                    setIsOpen(!isOpen)
                    toggleRef.current?.blur()}
                    }>
                    <i className={`fas ${isOpen ? "fa-chevron-left" : "fa-chevron-right"}`}></i>
                </button>
            </div>

            <nav className="sidebar-content">
                {[
                    { icon: "fa-solid fa-house", text: "Home" },
                    { icon: "fa-chart-line", text: "Dashboard" },
                    { divider: true },
                    { icon: "fa-solid fa-file", text: "All Papers" },
                    { icon: "fa-heart", text: "Favorites" },
                    { icon: "fa-solid fa-layer-group", text: "Groups" },
                    { divider: true },
                    { icon: "fa-fire", text: "About" },
                    { icon: "fa-gem", text: "GitHub" },
                ].map((item, idx) =>
                    item.divider ? (
                        <hr key={idx} />
                    ) : (
                        <div key={idx} className="nav-button">
                            <i className={`fas ${item.icon}`}></i>
                            {isOpen && <span>{item.text}</span>}
                        </div>
                    )
                )}
            </nav>
        </aside>
    );
}
