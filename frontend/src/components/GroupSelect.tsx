import "./GroupSelect.css"
import CreatableSelect from 'react-select/creatable';

export default function GroupSelect() {

    const options = [
        { value: 'bio', label: 'Bio' },
        { value: 'chem', label: 'Chem' },
        { value: 'physics', label: 'Physics' }
    ]

    return (
        <>
            <div className={"group-dropdown"}>
               <CreatableSelect placeholder={"Choose tag"} isMulti options={options}/>
            </div>
        </>
    )
}