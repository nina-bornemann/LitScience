type EditButtonProps = {
    onEdit: (id:string, dto:PaperDto) => void
}

export default function EditButton(props:Readonly<EditButtonProps>) {



    return (
        <>
            <button className={"detail-action-button"} onClick={handleChange}> ✏️ </button>
        </>
    )
}