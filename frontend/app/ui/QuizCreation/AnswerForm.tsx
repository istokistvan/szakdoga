import {Button, Input, Switch} from "@nextui-org/react";
import {Answer, Question} from "@/app/lib/definitions";
import {Dispatch, SetStateAction, useEffect, useState} from "react";

export default function AnswerForm(props: {
    type: string | undefined,
    setItems: Dispatch<SetStateAction<Question>>
}) {

    const [state, setState] = useState<Answer[]>([])

    useEffect(() => {
        if (props.type === 'ONE_CHOICE' || props.type === 'MULTIPLE_CHOICE') {
            setState([
                {answer: "", isCorrect: true, errorTolerance: 0},
                {answer: "", isCorrect: false, errorTolerance: 0},
            ]);
        } else {
            setState([{answer: "", isCorrect: true, errorTolerance: 0}]);
        }
    }, [props.type]);

    const handleSaveAnswer = () => {
        props.setItems((prevState) => ({...prevState, answers: state}));
    }

    const renderInputs = () => {
        switch (props.type) {
            case 'MULTIPLE_CHOICE':
                return (
                    <div
                        className="flex flex-col gap-5"
                    >
                        {state.map((item, index) => (
                            <div key={index} className="flex gap-5">
                                <Input
                                    name="answer"
                                    label="Answer"
                                    isRequired
                                    value={item.answer}
                                    variant="underlined"

                                    onChange={
                                        (e) => {
                                            setState(
                                                (prevState) => {
                                                    prevState[index].answer = e.target.value;
                                                    return [...prevState];
                                                }
                                            )
                                        }
                                    }
                                />
                                <Switch
                                    name="isCorrect"
                                    color="success"

                                    isSelected={item.isCorrect}

                                    onChange={
                                        (e) => {
                                            setState(
                                                (prevState) => {
                                                    prevState[index].isCorrect = e.target.checked;
                                                    return [...prevState];
                                                }
                                            )
                                        }
                                    }
                                />
                            </div>
                        ))}
                        <Button
                            onClick={() => setState(
                                (prevState) => [
                                    ...prevState,
                                    {answer: "", isCorrect: false, errorTolerance: 0}
                                ]
                            )}>
                            New answer
                        </Button>
                    </div>
                )
            case 'ONE_CHOICE':
                return (
                    <div
                        className="flex flex-col gap-5"
                    >
                        {state.map((item, index) => (
                            <div key={index} className="flex gap-5">
                                <Input
                                    name="answer"
                                    label="Answer"
                                    isRequired
                                    value={item.answer}
                                    variant="underlined"
                                    className="w-3/4"

                                    onChange={
                                        (e) => {
                                            setState(
                                                (prevState) => {
                                                    prevState[index].answer = e.target.value;
                                                    return [...prevState];
                                                }
                                            )
                                        }
                                    }
                                />

                                <input
                                    type="radio"
                                    name="isCorrect"
                                    defaultChecked
                                    onChange={
                                        () => {
                                            setState(
                                                (prevState) => {
                                                    prevState.forEach((item) => item.isCorrect = false);
                                                    prevState[index].isCorrect = true;
                                                    return [...prevState];
                                                }
                                            )
                                        }
                                    }
                                />
                            </div>
                        ))}
                        <Button
                            onClick={() => setState(
                                (prevState) => [
                                    ...prevState,
                                    {answer: "", isCorrect: false, errorTolerance: 0}
                                ]
                            )}>
                            New answer
                        </Button>
                    </div>
                )
            case 'NUMBER':
                return (
                    <div className="flex gap-5">
                        <Input
                            name="answer"
                            label="Answer"
                            type="number"
                            isRequired
                            variant="underlined"

                            onChange={(e) => setState((prevState) => {
                                const newState = [...prevState];
                                newState[0].answer = e.target.value;
                                return newState;
                            })}
                        />

                        <Input
                            name="errorTolerance"
                            label="Error tolerance"
                            type="number"
                            isRequired
                            variant="underlined"
                            onChange={(e) => setState((prevState) => {
                                const newState = [...prevState];
                                newState[0].errorTolerance = Number(e.target.value);
                                return newState;
                            })}
                        />
                    </div>
                )
            default:
                return null
        }
    }

    return (
        <>
            {renderInputs()}
            <Button onClick={handleSaveAnswer}>Save answers</Button>
        </>
    )
}