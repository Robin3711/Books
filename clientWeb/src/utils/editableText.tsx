import { useState } from "react";

export function EditableText(props: { value: string, onUpdate: (value: string) => Promise<void> }) {

    const [editing, setEditing] = useState(false);
    const [updating, setUpdating] = useState(false);

    const value = props.value;

   async function handleUpdate(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        const form = e.currentTarget;
        const value = form.value.value;
        try {
            setUpdating(true);
            await props.onUpdate(value);
        }
        catch (error) {
            setUpdating(false);
            // message d'erreur
        }
        setUpdating(false);
    };

    return (
        <>
            {editing ? (

                <form onSubmit={handleUpdate}>
                    <input name="value" defaultValue={value} />
                    <button type="submit" disabled={updating}>Edit</button>
                </form>

            ) : (
                <>
                    <span>{value}</span>
                    <button onClick={() => setEditing(true)}>Edit</button>
                </>
            )}
        </>
    );
}