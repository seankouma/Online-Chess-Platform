import React from 'react';
import AboutCard from "./AboutCard";

export default function Team(props) {
    return (
        <AboutCard pic={props.pic} title={props.teamName} text={props.missionStatement} team={true} />
    );
}
