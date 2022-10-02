import React from 'react';
import AboutCard from "./AboutCard";

export default function Person(props) {
    return (
        <AboutCard pic={props.pic} title={props.name} subTitle={props.homeTown} text={props.bio} />
    );
}
