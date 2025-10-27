import "./GroupSelect.css"
import { TagGroup, Tag, Input, IconButton } from 'rsuite';
import PlusIcon from '@rsuite/icons/Plus';
import {useState} from "react";
import 'rsuite/Tag/styles/index.css'
import type {Paper} from "../model/Paper.tsx";

type GroupSelectProps = {
    paper:Paper
    onGroupUpdate: (tags:string[]) => void
}

export default function GroupSelect(props:Readonly<GroupSelectProps>){
    const [tags, setTags] = useState<string[]>(props.paper.group);
    const [typing, setTyping] = useState(false);
    const [inputValue, setInputValue] = useState('');

    const removeTag = (tag: string) => {
        const nextTags = tags.filter(item => item !== tag);
        setTags(nextTags);
        props.onGroupUpdate(nextTags)
    };

    const addTag = () => {
        const nextTags = inputValue ? [...tags, inputValue] : tags;
        setTags(nextTags);
        setTyping(false);
        setInputValue('');
        props.onGroupUpdate(nextTags)
    };

    const handleButtonClick = () => {
        setTyping(true);
    };

    const renderInput = () => {
        if (typing) {
            return (
                <Input
                    className="tag-input"
                    size="xs"
                    style={{width: 70}}
                    value={inputValue}
                    autoFocus={true}
                    onChange={setInputValue}
                    onBlur={addTag}
                    onPressEnter={addTag}
                />
            );
        }

        return (
            <IconButton
                className="tag-add-btn"
                onClick={handleButtonClick}
                icon={<PlusIcon/>}
                appearance="ghost"
                size="xs"
            />
        );
    };

    return (
            <TagGroup>
                {tags.map((item, index) => (
                    <Tag key={index} closable onClose={() => removeTag(item)}>
                        {item}
                    </Tag>
                ))}
                {renderInput()}
            </TagGroup>
    );
};
