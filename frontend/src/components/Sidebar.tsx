import {useRef, useState} from "react";
import "./Sidebar.css";
import {useNavigate} from "react-router-dom";

export default function Sidebar() {
    const [isOpen, setIsOpen] = useState(false);
    const [isGroupOpen, setIsGroupOpen] = useState(false);
    const toggleRef = useRef<HTMLButtonElement>(null);
    const nav = useNavigate();

    return (
        <aside className={`sidebar ${isOpen ? "open" : "collapsed"}`}>
            <div className="sidebar-header">
                {isOpen && <span className="sidebar-title"> LitScience</span>}
                <button ref={toggleRef} className="toggle-btn" onClick={() => {
                    setIsOpen(!isOpen)
                    toggleRef.current?.blur()}
                    }>
                    <i className={`fas ${isOpen ? "fa-chevron-left" : "fa-chevron-right"}`}></i>
                </button>
            </div>

            <nav className="sidebar-content">
                {
                [
                    { icon: "fa-solid fa-house", text: " Home", link: "/home"},
                    { icon: "fa-chart-line", text: " Dashboard", link: "/"},
                    { divider: true },
                    { icon: "fa-solid fa-file", text: " All Papers", link: "/all"},
                    { icon: "fa-heart", text: " Favorites", link: "/favorites" },
                    { icon: "fa-solid fa-layer-group", text: " Groups", link: "/groups" },
                    { divider: true },
                    { icon: "fa-fire", text: " About", link: "/home" },
                ].map((item, idx) =>
                    item.divider ? (
                        <hr key={idx} />
                    ) : (
                        <div key={idx} className="nav-button">
                            <a className={"sidebar-text"} onClick={() =>
                            {
                                if (item.text === " Groups") {
                                    setIsGroupOpen(!isGroupOpen)
                                }
                                else {
                                    nav(item.link!)
                                    setIsOpen(!isOpen)
                                }
                            }}>
                                <i className={`fas ${item.icon}`}></i>
                                {isOpen && <span>{item.text}</span>}
                                {isGroupOpen && item.text === " Groups" && <a>
                                    chemistry
                                </a>}
                            </a>
                        </div>
                    )
                )}
            </nav>
        </aside>
    );
}
